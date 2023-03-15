import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { IntructionTexts, IntructionTitles } from 'src/app/consts/instruction-consts';
import { TypeReading } from 'src/app/consts/type-consts';
import { ThemeService } from 'src/app/services/theme.service';
import { DialogData } from '../../account/delete-user-dialog/delete-user-dialog.component';

@Component({
  selector: 'app-reading-instruction',
  templateUrl: './reading-instruction.component.html',
  styleUrls: ['./reading-instruction.component.css']
})
export class ReadingInstructionComponent implements OnInit {
  type!: string;
  title!: string;
  text_line1!:string;
  text_line2!:string;

  constructor(protected themeService: ThemeService, 
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.type = this.data.type
    switch(this.type){
      case TypeReading.FINDING_NUMBERS:{
        this.title = IntructionTitles.FINDING_NUMBERS;
        this.text_line1=IntructionTexts.FINDING_NUMBERS_LINE1;
        this.text_line2=IntructionTexts.FINDING_NUMBERS_LINE2;
        
      }break;
      case TypeReading.READING_WITH_QUIZ:{
        this.title = IntructionTitles.QUIZ;
        this.text_line1=IntructionTexts.FINDING_NUMBERS_LINE1;
        this.text_line2=IntructionTexts.FINDING_NUMBERS_LINE2;
      }break;
      case TypeReading.SCHULTZ:{
        this.title = IntructionTitles.SCHULTZ_TABLE;
        this.text_line1=IntructionTexts.SCHULTZ_TABLE_LINE1;
        this.text_line2=IntructionTexts.SCHULTZ_TABLE_LINE2;
      }
    }
  }
  SCHULTZ: string = TypeReading.SCHULTZ
  FINDING_NUMBERS: string = TypeReading.FINDING_NUMBERS
  QUIZ :string = TypeReading.READING_WITH_QUIZ

  linkToLevelChoice(){
    this.router.navigate(["/courses/reading/level",
    {
      type: this.type
    }])
  }
}
