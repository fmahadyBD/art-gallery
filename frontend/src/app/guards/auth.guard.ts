import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { AuthServiceService } from '../service/auth-service.service';



/*
This before Angular 15
Recommendation:
For existing projects or complex logic: Use Type 02 (Class-Based Guard) for consistency with other services and more extensibility.
*/
@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate
{
  constructor(
    private authService: AuthServiceService,
    private router:Router,
    @Inject(PLATFORM_ID) private platform:Object
  ){}


  canActivate(): boolean {
    if(this.authService.isLoggedIn()){
      return true;
    }else{
      this.router.navigate(['/login']);
      return false;
    }
     
    
  }


}