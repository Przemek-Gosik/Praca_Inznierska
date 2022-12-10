import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { MenuComponent } from './pages/menu/menu.component';
import { SigninComponent } from './pages/signin/signin.component';
import { SignupComponent } from './pages/signup/signup.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { MatIconModule, MatToolbarModule, MatMenuModule, MatSelectModule } from '@angular/material';
import { HttpClient,HttpClientModule } from '@angular/common/http';
import { ReadingComponent } from './pages/reading/reading.component';
import { WritingComponent } from './pages/writing/writing.component';
import { MemorizingComponent } from './pages/memorizing/memorizing.component';
import { AccountComponent } from './pages/account/account.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { ContactComponent } from './pages/contact/contact.component';
import { FooterComponent } from './pages/footer/footer.component';

import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input"

import { FormsModule } from "@angular/forms";
import { MatchPasswordDirective } from './directives/match-password.directive';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MenuComponent,
    SigninComponent,
    SignupComponent,
    PageNotFoundComponent,
    ReadingComponent,
    WritingComponent,
    MemorizingComponent,
    AccountComponent,
    SettingsComponent,
    ContactComponent,
    FooterComponent,
    MatchPasswordDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatIconModule,
    MatToolbarModule,
    MatMenuModule,
    MatSelectModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
