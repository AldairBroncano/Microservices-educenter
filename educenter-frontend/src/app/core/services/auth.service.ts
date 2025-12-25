import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private tokenKey = 'edu_token';
  private userSubject = new BehaviorSubject<any>(this.parseToken(this.getToken()));
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/auth/login`, credentials).pipe(
      tap((res) => {
        if (res && res.token) {
          this.setToken(res.token);

          const payload = this.parseToken(res.token);

          if (payload?.role && typeof window !== 'undefined') {
            localStorage.setItem('role', payload.role.replace(/^ROLE_/, '').toUpperCase());
          }

          this.userSubject.next(payload);
        }
      })
    );
  }

  register(payload: any) {
    return this.http.post<any>(`${environment.apiUrl}/auth/register`, payload);
  }

  logout() {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(this.tokenKey);
    }
    this.userSubject.next(null);
    // 🔹 Redirigir al login
    this.router.navigate(['/login']);
  }

  private setToken(token: string) {
    if (typeof window !== 'undefined') {
      localStorage.setItem(this.tokenKey, token);
    }
  }

  getToken(): string | null {
    if (typeof window !== 'undefined') {
      return localStorage.getItem(this.tokenKey);
    }
    return null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getUserRoles(): string[] {
    const payload = this.parseToken(this.getToken());

    if (!payload) return [];

    // Si viene "roles": ["ROLE_ADMIN"] o similar
    if (Array.isArray(payload.roles)) {
      return payload.roles.map((r: string) => r.replace(/^ROLE_/, '').toUpperCase());
    }

    // Si viene "role": "ADMIN" (como en tu backend actual)
    if (payload.role) {
      return [
        String(payload.role)
          .replace(/^ROLE_/, '')
          .toUpperCase(),
      ];
    }

    return [];
  }

  hasAnyRole(expected: string[] = []): boolean {
    const roles = this.getUserRoles();
    return expected.some((r) => roles.includes(r));
  }

  private parseToken(token: string | null): any {
    if (!token) return null;
    try {
      const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (e) {
      return null;
    }
  }

  getUsername(): string | null {
    const payload = this.parseToken(this.getToken());
    return payload ? payload.sub || payload.user || null : null;
  }

  getRole(): string | null {
    if (typeof window !== 'undefined') {
      // 👈 IMPORTANTE
      return localStorage.getItem('role');
    }
    return null;
  }

  // devuelve array normalizado ['ADMIN','TEACHER'...]
  getRolesNormalized(): string[] {
    const roles = this.getUserRoles();
    if (!roles) return [];
    return roles.map((r: string) => r.replace(/^ROLE_/, '').toUpperCase());
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  getUserId(): number | null {
    const payload = this.parseToken(this.getToken());
    console.log('Payload decodificado:', payload);
    if (!payload) return null;

    return payload.userId || null;
  }

  public restoreUserFromStorage(): any {
    const token = this.getToken();
    const payload = this.parseToken(token);

    if (typeof window !== 'undefined') {
      const storedRole = localStorage.getItem('role');
      if (storedRole && payload) {
        payload.role = storedRole;
      }
    }

    this.userSubject.next(payload); // 🔥 ACTUALIZA EL ESTADO!
    return payload;
  }
}
