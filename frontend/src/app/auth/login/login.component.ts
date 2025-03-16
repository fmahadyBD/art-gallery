import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from '../../service/auth-service.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthServiceService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    })
  }


  ngOnInit(): void {

    if (this.authService.isLoggedIn()) {
      this.router.navigate(['']);
    }
  }


  onSubmit() {
    if (this.loginForm.invalid) {
      this.errorMessage = "Please fill valid information";
      return;
    }

    const { email, password } = this.loginForm.value;
    this.authService.login({ email, password }).subscribe({
      next: res => {
        this.successMessage = "Login successful";
        this.errorMessage = null;
        if(this.authService.isAdmin()){
          this.router.navigate(['']);
          return;
        }else if (this.authService.isUser()){
          this.router.navigate(['']);
        }

        
      },
      error: (error) => {
        this.errorMessage = error.error.message
        this.successMessage = null;
      }
    })

  }

}