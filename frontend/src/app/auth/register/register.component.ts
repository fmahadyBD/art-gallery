import { Component } from '@angular/core';
import { RegisterRequest } from '../../model/RegisterRequest.model';
import { GENDER } from '../../enums/gender.enum';
import { AuthServiceService } from '../../service/auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerRequest: RegisterRequest={
    email:'',
    password:'',
    dob: new Date(),
    gender:GENDER.MALE
  };

  genders = Object.values(GENDER);
  errorMessage: string | null = null;
  constructor(private authService:AuthServiceService,private router:Router){}

  register():void{
    this.authService.register(this.registerRequest).subscribe({
      next:(response)=>{
        alert('Registration successfll! Active your account. check your email');
        this.router.navigate(['/login']);
      },
      error:(error)=>{
        this.errorMessage='Registration failed. Please Try again';
      }
    })
  }

}
