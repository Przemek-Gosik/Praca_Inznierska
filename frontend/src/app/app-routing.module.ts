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
import { WritingTestComponent } from './pages/writing/writingtexts/writing-test/writing-test.component';
import { SchubertComponent } from './pages/reading/schubert/schubert.component';
import { AuthGuard } from './helpers/auth.guard';
import { RoleConsts } from './consts/role-consts';
import { UsersComponent } from './pages/admin/users/users.component';
import { ReportsComponent } from './pages/admin/reports/reports.component';
import { ChooseLevelReadingComponent } from './pages/reading/choose-level-reading/choose-level-reading.component';
import { ChooseLevelMemorizingComponent } from './pages/memorizing/choose-level-memorizing/choose-level-memorizing.component';
import { MemorizngMnemonicsComponent } from './pages/memorizing/memorizng-mnemonics/memorizng-mnemonics.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent},
  { path: 'account/signin', component: SigninComponent},
  { path: 'account/signin/signup', component: SignupComponent},
  { path: 'account/signin/signup/verification', component: VerificationCodeComponent},
  { path: 'reading', component: ReadingComponent},
  { path: 'reading/level',component:ChooseLevelReadingComponent},
  { path: 'reading/level/schultz',component: SchubertComponent},
  { path: 'writing', component: WritingComponent},
  { path: 'writing/course', component: WritingcoursesComponent},
  { path: 'writing/course/lesson',component: WritinglessonComponent},
  { path: 'writing/text',component:WritingtextsComponent},
  { path: 'writing/text/test',component:WritingTestComponent},
  { path: 'memorizing', component: MemorizingComponent},
  { path: 'memorizing/level', component: ChooseLevelMemorizingComponent},
  { path: 'memorizing/level/mnemonics', component: MemorizngMnemonicsComponent},
  { path: 'account', component: AccountComponent},
  { path: 'account/editaccount', component: EditAccountComponent,canActivate: [AuthGuard],data: {roles: [RoleConsts.USER]}},
  { path: 'account/admin/users', component: UsersComponent, canActivate: [AuthGuard],data: {roles: [RoleConsts.ADMIN]}},
  { path: 'account/admin/reports', component: ReportsComponent, canActivate: [AuthGuard],data: {roles: [RoleConsts.ADMIN]}},
  { path: 'settings', component: SettingsComponent},
  { path: 'contact', component: ContactComponent}, 
  { path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
