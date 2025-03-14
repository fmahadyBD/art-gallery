import { Component, OnInit } from '@angular/core';
import { MyProfileService } from '../../service/my-profile.service';
import { response } from 'express';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-my-profile',
  standalone: false,
  templateUrl: './my-profile.component.html',
  styleUrl: './my-profile.component.css'
})
export class MyProfileComponent implements OnInit{
  profile: any; // This will store the profile data

  private baseUrl = 'http://localhost:8080/api/profile/';

  constructor(private profileService: MyProfileService) { }

  ngOnInit(): void {
    // Assuming the username is available in the localStorage or from a route param
    const username = localStorage.getItem('username');
    if (username) {
      this.getProfile(username);
    }
  }

  private getProfile(username: string) {
    this.profileService.profileByUsername(username).subscribe({
      next: (response) => {
        this.profile = response;
      },
      error: (err) => {
        console.error('Error fetching profile data: ', err);
      }
    });
  }


}
