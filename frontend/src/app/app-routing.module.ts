import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './pages/account/account.component';
import { ContactComponent } from './pages/contact/contact.component';
import { HomeComponent } from './pages/home/home.component';
import { MemorizingComponent } from './pages/memorizing/memorizing.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { ReadingComponent } from './pages/reading/reading.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { SigninComponent } from './pages/signin/signin.component';
import { SignupComponent } from './pages/signup/signup.component';
import { WritingComponent } from './pages/writing/writing.component';
import { VerificationCodeComponent } from './pages/signup/verification-code/verification-code.component';
import { WritingcoursesComponent } from './pages/writing/writingcourses/writingcourses.component';
import { WritingtextsComponent } from './pages/writing/writingtexts/writingtexts.component';
import { WritinglessonComponent } from './pages/writing/writingcourses/writinglesson/writinglesson.component';
import { EditAccountComponent } from './pages/account/edit-account/edit-account.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent},
  { path: 'account/signin', component: SigninComponent},
  { path: 'account/signin/signup', component: SignupComponent},
  { path: 'account/signin/signup/verification', component: VerificationCodeComponent},
  { path: 'reading', component: ReadingComponent},
  { path: 'writing', component: WritingComponent},
  { path: 'writing/course', component: WritingcoursesComponent},
  { path: 'writing/course/lesson',component: WritinglessonComponent},
  { path: 'writing/text',component:WritingtextsComponent},
  { path: 'memorizing', component: MemorizingComponent},
  { path: 'account', component: AccountComponent},
  { path: 'account/editaccount', component: EditAccountComponent},
  { path: 'settings', component: SettingsComponent},
  { path: 'contact', component: ContactComponent}, 
  { path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
