import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private tokenKey = 'edu_token';
  private userSubject = new BehaviorSubject<any>(this.parseToken(this.getToken()));
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/auth/login`, credentials).pipe(
      tap((res) => {
        if (res && res.token) {
          this.setToken(res.token);
          this.userSubject.next(this.parseToken(res.token));
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
    const p = this.parseToken(this.getToken());
    return p && p.roles ? p.roles : [];
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
}
