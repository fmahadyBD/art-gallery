import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MyProfileService {
  
  private baseUrl = 'http://localhost:8080/api/profile/';

 constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
        'Authorization': `Bearer ${token}`
    });
  }



 profileByUsername(username: string): Observable<any> {
  return this.http.get(`${this.baseUrl}get/username/${username}`, {
    headers: this.getHeaders()
  }).pipe(
    catchError(this.handleError) 
  );
}

private handleError(error: any) {
  console.error("An error occurred: ", error);
  return throwError(() => new Error(error.message || "Server Error!"));
}





}
