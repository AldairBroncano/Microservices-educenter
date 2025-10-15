import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
})
export class LoginComponent {
  credentials = {
    email: '',
    password: '',
  };

  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  private normalizeRoles(rawRoles: any): string[] {
    if (!rawRoles) return [];
    const arr = Array.isArray(rawRoles) ? rawRoles : [rawRoles];
    return arr.map((r: any) => String(r)).map((r: string) => r.replace(/^ROLE_/, '').toUpperCase());
  }

  onLogin() {
    console.log('[DEBUG] POST URL:', `${environment.apiUrl}/auth/login`);
    console.log('[DEBUG] BODY:', this.credentials);

    this.authService.login(this.credentials).subscribe({
      next: (res) => {
        console.log('Login OK:', res);

        // Guardar token en localStorage
        this.authService['setToken'](res.token);

        // Normalizar rol devuelto por el backend
        const roles = this.normalizeRoles(res.role);

        console.log('Rol detectado:', roles);

        // Redirección por tipo de usuario
        if (roles.includes('ADMIN')) {
          this.router.navigate(['/admin-dashboard']);
        } else if (roles.includes('TEACHER')) {
          this.router.navigate(['/teacher-dashboard']);
        } else if (roles.includes('STUDENT')) {
          this.router.navigate(['/student-dashboard']);
        } else {
          this.router.navigate(['/courses']);
        }
      },
      error: (err) => {
        console.error('Error de login (full error):', err);
        const status = err?.status ?? 'no-status';
        const body = err?.error ?? err?.message ?? JSON.stringify(err);
        this.errorMessage = `Error ${status}: ${
          typeof body === 'string' ? body : JSON.stringify(body)
        }`;
      },
    });
  }
}
