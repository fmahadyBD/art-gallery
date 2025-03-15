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
  loading: boolean = true;
  error: string | null = null;

  constructor(private artService: PostService) { }

  ngOnInit(): void {
    this.loadArtsWithoutPagination();
  }

  loadArtsWithoutPagination(): void {
    this.loading = true;
    this.artService.getAllArtsWithoutPagination().subscribe({
      next: (response) => {
        console.log('API Response:', response);
        if (response.success) {
          this.arts = response.data;
        } else {
          this.error = response.message || 'Failed to load artworks';
        }
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching arts:', err);
        this.error = 'An error occurred while fetching artworks';
        this.loading = false;
      }
    });
  }
}