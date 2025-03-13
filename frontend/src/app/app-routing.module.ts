import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { authGuard } from './guards/auth.guard';
import { RegisterComponent } from './auth/register/register.component';
import { UpdateCategoryComponent } from './admin/update-category/update-category.component';
import { NewCategoryComponent } from './admin/new-category/new-category.component';
import { adminGuard } from './guards/admin.guard';
import { CreatePostComponent } from './create-post/create-post.component';
import { userGuard } from './guards/user.guard';


const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'',component:HomeComponent,canActivate:[authGuard]},
  // {path:'admin',component:HomeComponent},
  {path:'register',component:RegisterComponent},
  // { path: '**', redirectTo: '/login' },


  // Category

  {path:'new-category',component: NewCategoryComponent,canActivate:[adminGuard]},
  {path:'update-category',component:UpdateCategoryComponent,canActivate:[adminGuard]},


  // user

  {path:'post',component:CreatePostComponent,canActivate:[userGuard]}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
