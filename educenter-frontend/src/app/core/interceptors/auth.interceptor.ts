import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const token = auth.getToken();

  // 🚫 No agregar token en rutas públicas
  if (
    req.url.includes('/auth/login') ||
    req.url.includes('/auth/register') ||
    req.url.includes('/auth/forgot-password')
  ) {
    return next(req);
  }

  // ✅ Agregar token solo si existe y no es ruta pública
  if (token) {
    console.log('🟢 Token enviado para:', req.url);
    const cloned = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    });
    return next(cloned);
  }
  console.warn('⚠️ No se encontró token para:', req.url);
  return next(req);
};
