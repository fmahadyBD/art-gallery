import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { RegisterComponent } from './auth/register/register.component';
import { UpdateCategoryComponent } from './admin/update-category/update-category.component';
import { NewCategoryComponent } from './admin/new-category/new-category.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { MyProfileComponent } from './user/my-profile/my-profile.component';
import { MyFooterComponent } from './my-footer/my-footer.component';
import { ForgetPasswordComponent } from './auth/forget-password/forget-password.component';
import { ChangePasswordComponent } from './auth/change-password/change-password.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    AdminComponent,
    RegisterComponent,
    UpdateCategoryComponent,
    NewCategoryComponent,
    NavbarComponent,
    CreatePostComponent,
    MyProfileComponent,
    MyFooterComponent,
    ForgetPasswordComponent,
    ChangePasswordComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    provideClientHydration(withEventReplay()),
    provideHttpClient(
      withFetch()
    )
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
