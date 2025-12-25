import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    // 1️⃣ Primero: verificar si está logueado
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }

    // 2️⃣ Luego: validar roles
    const expectedRoles = route.data['roles'] as string[];
    if (!this.auth.hasAnyRole(expectedRoles)) {
      this.router.navigate(['/unauthorized']); // 👈 Página de acceso denegado
      return false;
    }

    return true; // Puede acceder
  }
}
