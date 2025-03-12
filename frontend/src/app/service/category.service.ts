import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseUrl = 'http://localhost:8080/api/categories/';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
     'Authorization': `Bearer ${token}`
    });
  }

  newCategory(categoryRequest: any) {
    return this.http.post(this.baseUrl+'save', categoryRequest, { headers: this.getHeaders() });
  }


  private handleError(error: any){
    console.error("An error occured: ",error);
    return throwError(()=> new Error(error.message || "Server Error!"));

  }

}
