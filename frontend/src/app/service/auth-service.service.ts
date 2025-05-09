import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { LoginRequest } from '../model/LogInRequest.model';
import { AuthResponse } from '../model/AuthResponse.model';
import { RegisterRequest } from '../model/RegisterRequest.model';


// @Injectable({
//   providedIn: 'root'
// })
// export class AuthServiceService {

//   private baseUrl = 'http://localhost:8080/api/authenticate/';
//   private headers = new HttpHeaders({
//     'Content-Type': 'application/json'
//   });

//   private userRoleSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);
//   public userRole$: Observable<String | null> = this.userRoleSubject.asObservable();


//   constructor(@Inject(PLATFORM_ID) private platformId: Object, private http: HttpClient, private router: Router) {
//     if (this.isBrowser()) {
//       const storedRole = localStorage.getItem('userRole');
//       this.userRoleSubject.next(storedRole);
//     }
//   }


//   private isBrowser(): boolean {
//     return isPlatformBrowser(this.platformId);
//   }

//   // register:

//   register(registerRequest: RegisterRequest): Observable<AuthResponse> {
//     return this.http.post<AuthResponse>(this.baseUrl + 'register', registerRequest, { headers: this.headers });
//   }

//   // login

//   login(loginRequest: LoginRequest): Observable<AuthResponse> {
//     return this.http.post<AuthResponse>(this.baseUrl + 'login', loginRequest, { headers: this.headers }).pipe(
//       map((response: AuthResponse) => {
//         if (this.isBrowser() && response.token) {
//           localStorage.setItem('token', response.token);
//           const decodedToken = this.decodeToken(response.token);
//           localStorage.setItem('userRole', decodedToken.role);
//           this.userRoleSubject.next(decodedToken.role);
//         }
//         return response;
//       })
//     )
//   }

//   // decode token
//   decodeToken(token: string): any {
//     const payload = token.split('.')[1];
//     return JSON.parse(atob(payload));
//   }

//   //logout

//   logout(): void {
//     if (this.isBrowser()) {
//       localStorage.removeItem('token');
//       localStorage.removeItem('userRole');
//       this.userRoleSubject.next(null);
//       this.router.navigate(['login']);

//     }
//   }



//   isLoggedIn(): boolean {
//     if (this.isBrowser()) {
//       const token = localStorage.getItem('token');
//       if (token) {
//         if (!this.isTokenExpired(token)) {
//           return true;
//         }
//         this.logout();
//       }
//     }
//     return false;
//   }

//   isAdmin(): boolean {
//     return this.isBrowser() && localStorage.getItem('userRole') === 'ADMIN';
//   }

//   isUser(): boolean {
//     return this.isBrowser() && localStorage.getItem('userRole') === 'USER';
//   }


//   isTokenExpired(token: string): boolean {
//     const decodedToken = this.decodeToken(token);
//     return Date.now() > decodedToken.exp * 1000;
//   }

// }



@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private baseUrl = 'http://localhost:8080/api/authenticate/';
  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  private userRoleSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);
  public userRole$: Observable<string | null> = this.userRoleSubject.asObservable();

  constructor(@Inject(PLATFORM_ID) private platformId: Object, private http: HttpClient, private router: Router) {

    if (this.isBrowser()) {
      const storedRole = localStorage.getItem('userRole');
      const token = localStorage.getItem('token');
      if (token && !this.isTokenExpired(token)) {
        this.userRoleSubject.next(storedRole);
      } else {
        this.logout();
      }
    }
  }

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  // register:
  register(registerRequest: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.baseUrl + 'register', registerRequest, { headers: this.headers });
  }

  // login
  login(loginRequest: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.baseUrl + 'login', loginRequest, { headers: this.headers }).pipe(
      map((response: AuthResponse) => {
        if (this.isBrowser() && response.token) {
          localStorage.setItem('token', response.token);
          const decodedToken = this.decodeToken(response.token);
          localStorage.setItem('userRole', decodedToken.role);
          this.userRoleSubject.next(decodedToken.role);
        }
        return response;
      })
    );
  }

  // decode token
  decodeToken(token: string): any {
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload));
  }

  // logout
  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem('token');
      localStorage.removeItem('userRole');
      this.userRoleSubject.next(null);
      this.router.navigate(['login']);
    }
  }

  isLoggedIn(): boolean {
   
      const token = localStorage.getItem('token');
      if (token) {
        if (!this.isTokenExpired(token)) {
          return true;
        }
        this.logout();
      }
    
    return false;
  }

  isAdmin(): boolean {
    return this.isBrowser() && localStorage.getItem('userRole') === 'ADMIN';
  }

  isUser(): boolean {
    return this.isBrowser() && localStorage.getItem('userRole') === 'USER';
  }

  isTokenExpired(token: string): boolean {
    const decodedToken = this.decodeToken(token);
    return Date.now() > decodedToken.exp * 1000;
  }

  userName(): any {
    if (this.isBrowser() && this.isLoggedIn()) {
      const token = localStorage.getItem('token');

      if (token) {
        const decoded = this.decodeToken(token);
        return decoded.sub;
      }
    }
    return null;
  }
}