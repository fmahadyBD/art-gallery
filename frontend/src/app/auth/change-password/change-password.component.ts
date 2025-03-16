import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthServiceService } from '../../service/auth-service.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-change-password',
  standalone: false,
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent implements OnInit {
  passwordForm!: FormGroup;
  loading = false;
  submitted = false;
  errorMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthServiceService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.passwordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      verificationCode: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validator: this.mustMatch('password', 'confirmPassword')
    });
  }

  // Convenience getter for easy access to form fields
  get f() { return this.passwordForm.controls; }

  // Custom validator to check if passwords match
  mustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];

      if (matchingControl.errors && !matchingControl.errors['mustMatch']) {
        // Return if another validator has already found an error
        return;
      }

      // Set error if validation fails
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ mustMatch: true });
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  updatePassword() {
    this.submitted = true;

    // Stop here if form is invalid
    if (this.passwordForm.invalid) {
      return;
    }

    this.loading = true;
    const { email, password, verificationCode } = this.passwordForm.value;
    
    // Convert verificationCode to number as required by the backend
    const code = Number(verificationCode);

    this.authService.updatePassword(email, password, code).subscribe({
      next: (response: any) => {
        // Navigate to login page after successful password change
        this.router.navigate(['/login']);
      },
      error: (error:any) => {
        this.errorMessage = error?.error?.Error || 'Failed to update password. Please try again.';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

}