import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { provideHttpClient, withFetch } from '@angular/common/http';
import { AdminComponent } from './admin/admin.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { HomeComponent } from './home/home.component';
import { MyFooterComponent } from './my-footer/my-footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UpdateCategoryComponent } from './update-category/update-category.component';
import { MyProfileComponent } from './user/my-profile/my-profile.component';
import { NewCategoryComponent } from './new-category/new-category.component';
import { AdashboardComponent } from './adashboard/adashboard.component';


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
    AdashboardComponent,


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
