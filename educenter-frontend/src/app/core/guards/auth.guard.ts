import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    // 1️⃣ Si no está logueado, redirige al login
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }

    // 2️⃣ Si la ruta tiene roles esperados, revisa si el usuario los cumple
    const expectedRoles = route.data['roles'] as string[] | undefined;
    if (expectedRoles && !this.auth.hasAnyRole(expectedRoles)) {
      // Si no tiene el rol, redirigimos a una página de acceso denegado o al home
      this.router.navigate(['/unauthorized']);
      return false;
    }

    // 3️⃣ Todo bien, puede entrar
    return true;
  }
}
