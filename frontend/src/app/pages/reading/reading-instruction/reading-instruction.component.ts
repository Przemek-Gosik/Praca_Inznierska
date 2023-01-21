import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TypeReading } from 'src/app/consts/type-consts';
import { ThemeService } from 'src/app/services/theme.service';
import { DialogData } from '../../account/delete-user-dialog/delete-user-dialog.component';

@Component({
  selector: 'app-reading-instruction',
  templateUrl: './reading-instruction.component.html',
  styleUrls: ['./reading-instruction.component.css']
})
export class ReadingInstructionComponent implements OnInit {

  constructor(protected themeService: ThemeService, 
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: string) { }

  ngOnInit(): void {
  }
  SCHULTZ: string = TypeReading.SCHULTZ
  FINDING_NUMBERS: string = TypeReading.FINDING_NUMBERS
  QUIZ :string = TypeReading.READING_WITH_QUIZ

  linkToLevelChoice(type:string){
    this.router.navigate(["/courses/reading/level",
    {
      type: type
    }])
  }
}
