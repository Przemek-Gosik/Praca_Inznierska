import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { WritingTextResult } from 'src/app/models/writing-model';
import { WritingService } from 'src/app/services/writing.service';

@Component({
  selector: 'app-writing-text-result-dialog',
  templateUrl: './writing-text-result-dialog.component.html',
  styleUrls: ['./writing-text-result-dialog.component.css']
})
export class WritingTextResultDialogComponent implements OnInit {
  saved: boolean = false
  result: WritingTextResult = {
    idWritingTextResult: 0,
    typedText: '',
    score: 0,
    startTime: '',
    time: 0,
    idText: 0
  }
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,private httpWriting: WritingService) { }

  ngOnInit(): void {
    this.result = this.data.result
    console.log(this.result)
  }

  calculatePrecision():string{
    var precision : number = 0
    precision =(this.result.score*1.0/this.result.typedText.length)*100 
    return precision.toFixed(2)+'%'
  }

  calculateSpeed():string{
    var speed :number = this.result.typedText.length*1.0/this.result.time
    return speed.toFixed(2)
  }

  saveResult(){
    this.httpWriting.saveTextResult(this.result);
  }
}
