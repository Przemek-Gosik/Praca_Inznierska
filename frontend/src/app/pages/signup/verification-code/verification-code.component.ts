import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VerificationCode } from 'src/app/models/verification-code';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css']
})
export class VerificationCodeComponent implements OnInit {

  title: string = "Weryfikacja konta"
  instruction: string = "Aby aktywować konto należy wpisać w poniższe pole kod weryfikacyjny otrzymany na adres e-mail (wskazany podczas rejestracji)."

  verificationCode: Partial<VerificationCode> = {};  
  
  constructor(private registrationService: RegistrationService,
    private router: Router) { }

  verify() {
    console.log(this.verificationCode)
     this.registrationService.verifyUser(this.verificationCode as VerificationCode).subscribe(
       result => console.log(result),
       error => console.log(error),
   )
   this.router.navigate(['/']);
  }

  ngOnInit(): void {
  }

}
