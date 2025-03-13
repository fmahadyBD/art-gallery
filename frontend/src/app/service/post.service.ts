import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PostRequest } from '../model/PostRequest.model';
import { Observable, throwError, catchError } from 'rxjs';
import { CategoryResponse } from '../model/CategoryResponse';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseUrl = 'http://localhost:8080/api/arts/';

  constructor(private http: HttpClient) { }

  // private getHeaders(): HttpHeaders {
  //   const token = localStorage.getItem('token');
  //   return new HttpHeaders({
  //       'Authorization': `Bearer ${token}`
  //   });
  // }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return token 
      ? new HttpHeaders({ 'Authorization': `Bearer ${token}` })
      : new HttpHeaders();
  }

  newPost(art: PostRequest, image: File): Observable<PostRequest> {
    const formData = new FormData();
    formData.append('art', new Blob([JSON.stringify(art)], { type: 'application/json' }));
    formData.append('image', image);

    return this.http.post<PostRequest>(this.baseUrl + 'save', formData, { headers: this.getHeaders() }).pipe(
      catchError(this.handelError)
    );
    
  }

  private handelError(error:any){
    console.error("An error occured: ",error);
    return throwError(()=>new Error(error.message|| "Server Error!"));
  }

  getCategories(): Observable<CategoryResponse> {
    return this.http.get<CategoryResponse>(this.baseUrl + 'get-categories', 
      { headers: this.getHeaders() }).pipe(
      catchError(this.handelError)
    );
  }

  // getAllArtsWithoutPagination():Observable<any>{
  //   return this.http.get(this.baseUrl+'get-all-art' , { headers: this.getHeaders() }).pipe(
  //     catchError(this.handelError)
  //   );
  // }

  // getAllArts(page: number, size: number): Observable<any> {
  //   return this.http.get(`${this.baseUrl}get-all?page=${page}&size=${size}`).pipe(
  //     catchError(this.handelError)
  //   );
  // }

  getAllArtsWithoutPagination():Observable<any>{
    return this.http.get(this.baseUrl + 'get-all-art').pipe( // Remove headers for public API
      catchError(this.handelError)
  );
  }
  

}
