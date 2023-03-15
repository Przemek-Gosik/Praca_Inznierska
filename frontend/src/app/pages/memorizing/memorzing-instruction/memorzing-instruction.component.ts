import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { IntructionTexts, IntructionTitles } from 'src/app/consts/instruction-consts';
import { TypeMemory } from 'src/app/consts/type-consts';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-memorzing-instruction',
  templateUrl: './memorzing-instruction.component.html',
  styleUrls: ['./memorzing-instruction.component.css']
})

export class MemorzingInstructionComponent implements OnInit {

  type!: string;
  title!: string;
  text_line1!:string;
  text_line2!:string;
  text_line3!: string;

  constructor(private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: any,
    protected themeService: ThemeService) { }

  ngOnInit(): void {
    this.type=this.data.type
    switch(this.type){
      case TypeMemory.MEMORY:{
        this.title = IntructionTitles.MEMORY
        this.text_line1 = IntructionTexts.MEMORY_LINE1
        this.text_line2 = IntructionTexts.MEMORZY_LINE2
        this.text_line3 = IntructionTexts.MEMORY_LINE3;
      }break;
      case TypeMemory.MNEMONICS:{
        this.title = IntructionTitles.MNEMONICS
        this.text_line1 = IntructionTexts.MNEMONICS1
        this.text_line2 = IntructionTexts.MNEMONICS2
        this.text_line3 = IntructionTexts.MNEMONICS3;
      }break;
    }
  }

  linkToLevelChoice():void{
    this.router.navigate(['/courses/memorizing/level',
    {
      type: this.type
    }])
  }

}
