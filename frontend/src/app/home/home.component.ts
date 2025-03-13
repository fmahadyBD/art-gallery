import { Component, OnInit } from '@angular/core';
import { PostService } from '../service/post.service';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  arts: any[] = [];
  totalPages: number = 0;
  currentPage: number = 0;
  pageSize: number = 5;

  constructor(private artService: PostService) { }

  ngOnInit(): void {
    // this.loadArts();
    this.loadArtsWithoutPagination();
  }

  loadArtsWithoutPagination(): void {
    this.artService.getAllArtsWithoutPagination().subscribe(
      response => {
        console.log('API Response:', response);  // Log response
        if (response.success) {
          this.arts = response.data;  // Set arts data

          
        }
      },
      error => {
        console.error('Error fetching arts:', error);  // Log error
      }
    );
  }

  // loadArts(): void {
  //   this.artService.getAllArts(this.currentPage, this.pageSize).subscribe(response => {
  //     if (response.success) {
  //       this.arts = response.data.content;
  //       this.totalPages = response.data.totalPages;
  //     }
  //   });
  // }

  // changePage(page: number): void {
  //   this.currentPage = page;
  //   this.loadArts();
  // }
  // getPages(): number[] {
  //   return Array.from({ length: this.totalPages }, (_, i) => i);
  // }
}
