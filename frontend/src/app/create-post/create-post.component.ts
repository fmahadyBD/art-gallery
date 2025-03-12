import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostRequest } from '../model/PostRequest.model';
import { PostService } from '../service/post.service';
import { CategoryService } from '../service/category.service';
import { Router } from '@angular/router';
import { Category } from '../model/Category.mode';

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



  constructor(
    private postService: PostService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(1)]],
      status: ['', Validators.required],
      artist: ['', Validators.required],
      categoryId: ['', Validators.required]
    });

    
    this.loadCategories();
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
      }
    });
  }
  

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input?.files && input.files[0]) {
      this.image = input.files[0];
    }
  }

  onSubmit(): void {
    if (this.formGroup.invalid) {
      alert("Please fill all required fields with valid data.");
      return;
    }

    if (this.image) {
      const createPost: PostRequest = {
        ...this.formGroup.value,
        categoryId: Number(this.formGroup.value.categoryId)
      };

      this.postService.newPost(createPost, this.image).subscribe({
        next: res => {
          console.log("Post created successfully!", res);
          this.router.navigate(['view-all-posts']);
        },
        error: err => {
          console.error("Error creating post", err);
        }
      });
    } else {
      alert("Please select an image file.");
    }
  }
}
