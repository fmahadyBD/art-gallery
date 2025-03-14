import { Component, OnInit } from '@angular/core';
import { MyProfileService } from '../../service/my-profile.service';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-my-profile',
  standalone: false,
  templateUrl: './my-profile.component.html',
  styleUrl: './my-profile.component.css',
  providers: [DatePipe]
})
export class MyProfileComponent implements OnInit {
  profile: any;
  loading: boolean = true;
  error: string | null = null;
  
  constructor(
    private profileService: MyProfileService, 
    private route: ActivatedRoute,
    private datePipe: DatePipe
  ) { }
  
  ngOnInit(): void {
    // Get username from the route parameters
    this.route.params.subscribe(params => {
      const username = params['username'];
      if (username) {
        this.getProfile(username);
      }
    });
  }
  
  private getProfile(username: string) {
    this.loading = true;
    this.profileService.profileByUsername(username).subscribe({
      next: (response) => {
        if (response.success) {
          this.profile = response.data;
          console.log('Profile data:', this.profile);
        } else {
          this.error = response.message || 'Failed to load profile';
        }
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching profile data: ', err);
        this.error = 'An error occurred while fetching profile data';
        this.loading = false;
      }
    });
  }
  
  // Helper method to format the date
  formatDate(date: string): string {
    if (!date) return 'Not available';
    return this.datePipe.transform(date, 'MMM d, yyyy') || date;
  }
}