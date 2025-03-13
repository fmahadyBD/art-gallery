import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthServiceService } from '../service/auth-service.service';

export const userGuard: CanActivateFn = (route, state) => {
  
  const authService = inject(AuthServiceService)
  const router = inject(Router)

  if(authService.isUser()){
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }


};
