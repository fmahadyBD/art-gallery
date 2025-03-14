import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostRequest } from '../model/PostRequest.model';
import { PostService } from '../service/post.service';
import { CategoryService } from '../service/category.service';
import { Router } from '@angular/router';
import { Category } from '../model/Category.mode';
import { AuthServiceService } from '../service/auth-service.service';

@Component({
  selector: 'app-create-post',
  standalone: false,
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.css'
})
export class CreatePostComponent {
  postRequest: PostRequest = {} as PostRequest;
  formGroup!: FormGroup;
  image: File | null = null;
  categories: Category[] = [];
  submitted: boolean = false;
  errorMessage: string = '';
  successMessage: string = '';
  
  constructor(
    private postService: PostService,
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthServiceService
  ) {}
  
  ngOnInit(): void {
    this.initializeForm();
    this.loadCategories();
  }
  
  initializeForm(): void {
    this.formGroup = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(1)]],
      status: ['', Validators.required],
      artist: ['', Validators.required],
      categoryId: ['', Validators.required]
    });
  }
  
  loadCategories() {
    this.postService.getCategories().subscribe({
      next: res => {
        console.log("Full API Response: ", res);
        this.categories = res.data;
        console.log("Extracted Categories: ", this.categories);
      },
      error: err => {
        console.error("Error loading categories: ", err);
        this.errorMessage = 'Failed to load categories. Please try again.';
      }
    });
  }
  
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input?.files && input.files[0]) {
      this.image = input.files[0];
    }
  }
  
  resetForm(): void {
    // Reset form values
    this.initializeForm();
    
    // Reset file input
    this.image = null;
    
    // Reset form state
    this.submitted = false;
    
    // Reset any file input element (needs to be done differently since it's not directly controlled by FormGroup)
    const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }
  
  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = '';
    this.successMessage = '';
    
    // Validate form and show error at the top
    if (this.formGroup.invalid) {
      this.errorMessage = "Please fill all required fields with valid data.";
      window.scrollTo(0, 0); // Scroll to top to show the error
      return;
    }
    
    // Validate image and show error at the top
    if (!this.image) {
      this.errorMessage = "Please select an image file.";
      window.scrollTo(0, 0); // Scroll to top to show the error
      return;
    }
    
    const createPost: PostRequest = {
      ...this.formGroup.value,
      categoryId: Number(this.formGroup.value.categoryId),
      username: this.authService.userName() || null
    };
    
    this.postService.newPost(createPost, this.image).subscribe({
      next: res => {
        console.log("Post created successfully!", res);
        
        // Option 1: Show success message and reset form for another post
        this.successMessage = "Artwork posted successfully! You can create another one below.";
        this.resetForm();
        window.scrollTo(0, 0);
        
        // Option 2: Navigate to view all posts (commented out)
        // this.router.navigate(['view-all-posts']);
      },
      error: err => {
        console.error("Error creating post", err);
        this.errorMessage = "Failed to create post. Please try again.";
        window.scrollTo(0, 0); // Scroll to top to show the error
      }
    });
  }
  
  // Add this method if you want to let the user choose to add another post
  createAnother(): void {
    this.resetForm();
    this.successMessage = '';
  }
}