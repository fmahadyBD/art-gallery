import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthServiceService } from '../service/auth-service.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthServiceService);
  const router = inject(Router);

  // Check if already on login page to prevent redirect loop
  if (state.url === '/login') {
    return true;
  }

  if (authService.isLoggedIn()) {
    return true;  // Allow navigation
  } else {
    router.navigate(['/login']);
    return false;
  }
};