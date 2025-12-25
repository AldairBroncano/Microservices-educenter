import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(): boolean {
    // Si no está logueado, redirige al login
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }
    return true; // Autenticado => puede entrar
  }
}
