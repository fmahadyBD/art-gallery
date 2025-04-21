import { Component, OnInit } from '@angular/core';
import { PostService } from '../service/post.service';

@Component({
  selector: 'app-adashboard',
  standalone: false,
  templateUrl: './adashboard.component.html',
  styleUrl: './adashboard.component.css'
})
export class AdashboardComponent implements OnInit {
  arts: any[] = [];
  categories: any[] = [];
  loading: boolean = true;
  error: string | null = null;

  constructor(private artService: PostService) {}

  ngOnInit(): void {
    this.loadArtsWithoutPagination();
    this.loadCategories();
  }

  loadArtsWithoutPagination(): void {
    this.loading = true;
    this.artService.getAllArtsWithoutPagination().subscribe({
      next: (response) => {
        console.log('API Response (Arts):', response);
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

  loadCategories(): void {
    this.artService.getCategories().subscribe({
      next: (response) => {
        console.log('API Response (Categories):', response);
        if (response) {
          this.categories = response.data;
        } else {
          this.error = response || 'Failed to load categories';
        }
      },
      error: (err) => {
        console.error('Error fetching categories:', err);
        this.error = 'An error occurred while fetching categories';
      }
    });
  }
}

