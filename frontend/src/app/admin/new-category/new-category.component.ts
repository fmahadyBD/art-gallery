import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../../service/category.service';
import { CategoryRequest } from '../../model/CategoryRequest.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-category',
  standalone: false,
  templateUrl: './new-category.component.html',
  styleUrl: './new-category.component.css'
})
export class NewCategoryComponent {

 categoryForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(private categoryService: CategoryService, private router: Router, private fb: FormBuilder) {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

  // addCategory(): void {
  //   if (this.categoryForm.valid) {
  //     this.categoryService.newCategory(this.categoryForm.value).subscribe({
  //       next: () => {
  //         this.successMessage = 'Category added successfully!';
  //         setTimeout(() => {
  //           this.router.navigate(['/new-category']); 
  //         }, 1500);
  //       },
  //       error: () => {
  //         this.errorMessage = 'Failed to add category. Please try again.';
  //       }
  //     });
  //   } else {
  //     this.errorMessage = 'Please enter a valid category name.';
  //   }
  // }

  addCategory(): void {
    if (this.categoryForm.valid) {
      const categoryRequest: CategoryRequest = this.categoryForm.value; 
  
      this.categoryService.newCategory(categoryRequest).subscribe({
        next: () => {
          this.successMessage = 'Category added successfully!';
          setTimeout(() => {
            this.router.navigate(['']); // Redirect after success
          }, 1500);
        },
        error: () => {
          this.errorMessage = 'Failed to add category. Please try again.';
        }
      });
    } else {
      this.errorMessage = 'Please enter a valid category name.';
    }
  }
  
}

