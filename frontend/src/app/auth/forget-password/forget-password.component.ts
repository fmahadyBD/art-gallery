import { Component } from '@angular/core';
import { AuthServiceService } from '../../service/auth-service.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { error } from 'console';

@Component({
  selector: 'app-forget-password',
  standalone: false,
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.css'
})
export class ForgetPasswordComponent {

  emailForm: FormGroup;
  email: string | null = null;

  constructor(
    private authService: AuthServiceService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.emailForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  sendVerificationCode() {
    const { email } = this.emailForm.value;
    this.authService.sendVerificationCode(email).subscribe({
      next: () => {
        this.router.navigate(['change-password']);
      },
      error: () => {
        console.log(error);
      }
    });
  }

}
