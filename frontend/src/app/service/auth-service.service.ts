import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginRequest } from '../model/LogInRequest.model';
import { AuthResponse } from '../model/AuthResponse.model';
import { response } from 'express';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private baseUrl = 'http://localhost:8080/api/auth/';
  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  private userRoleSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);
  public userRole$: Observable<String | null> = this.userRoleSubject.asObservable();


  constructor(@Inject(PLATFORM_ID) private platformId: Object, private http: HttpClient, private router: Router) {
    if (this.isBrowser()) {
      const storedRole = localStorage.getItem('userRole');
      this.userRoleSubject.next(storedRole);
    }
  }


  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  // login

  login(loginRequest: LoginRequest):Observable<AuthResponse>{
    return this.http.post<AuthResponse>(this.baseUrl + 'login', loginRequest, {headers: this.headers}).pipe(
      map((response:AuthResponse)=>{
             if(this.isBrowser() && response.token){
              localStorage.setItem('token', response.token);
              const decodedToken = this.decodeToken(response.token);
              localStorage.setItem('userRole', decodedToken.role);
              this.userRoleSubject.next(decodedToken.role);
             }
             return response;
      })
    )
  }

  // decode token
  decodeToken(token:string):any{
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload));
  }
}
