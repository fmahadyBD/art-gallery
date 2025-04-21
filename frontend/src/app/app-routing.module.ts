import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './home/home.component';


import { RegisterComponent } from './auth/register/register.component';
import { UpdateCategoryComponent } from './update-category/update-category.component';

import { CreatePostComponent } from './create-post/create-post.component';
import { AuthGuard } from './guards/auth.guard';
import { MyProfileComponent } from './user/my-profile/my-profile.component';
import { NewCategoryComponent } from './new-category/new-category.component';
import { AdashboardComponent } from './adashboard/adashboard.component';



const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'new-category', component: NewCategoryComponent },
  { path: 'update-category', component: UpdateCategoryComponent },
  { path: 'post', component: CreatePostComponent },
  { path: 'my-profile/:username', component: MyProfileComponent, canActivate: [AuthGuard] },
  {path:'dashboard',component:AdashboardComponent,canActivate:[AuthGuard]}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
