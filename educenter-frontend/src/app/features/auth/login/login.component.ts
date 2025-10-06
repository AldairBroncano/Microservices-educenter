import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
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

  onLogin() {
    console.log('[DEBUG] POST URL:', `${environment.apiUrl}/auth/login`);
    console.log('[DEBUG] BODY:', this.credentials);

    this.authService.login(this.credentials).subscribe({
      next: (res) => {
        console.log('Login OK:', res);
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Error de login (full error):', err);
        // muestra status / body si existe
        const status = err?.status ?? 'no-status';
        const body = err?.error ?? err?.message ?? JSON.stringify(err);
        this.errorMessage = `Error ${status}: ${
          typeof body === 'string' ? body : JSON.stringify(body)
        }`;
      },
    });
  }
}
