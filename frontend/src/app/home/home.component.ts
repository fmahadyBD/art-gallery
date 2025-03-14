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


  constructor(private artService: PostService) { }

  ngOnInit(): void {
    this.loadArtsWithoutPagination();
  }

  loadArtsWithoutPagination(): void {
    this.artService.getAllArtsWithoutPagination().subscribe(
      response => {
        console.log('API Response:', response); 
        if (response.success) {
          this.arts = response.data;  
          
          
        }
      },
      error => {
        console.error('Error fetching arts:', error);
      }
    );
  }

  
}
