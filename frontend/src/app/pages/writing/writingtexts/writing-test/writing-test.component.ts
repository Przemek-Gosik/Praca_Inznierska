import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WritingText } from 'src/app/models/writing-model';
import { TimerService } from 'src/app/services/timer.service';
import { WritingService } from 'src/app/services/writing.service';

@Component({
  selector: 'app-writing-test',
  templateUrl: './writing-test.component.html',
  styleUrls: ['./writing-test.component.css']
})
export class WritingTestComponent implements OnInit {

  id: number = 0
  isDrawed: boolean = false
  writingText: WritingText = {
    idFastWritingText: 0,
    text: "",
    title: "",
    level: ""
  }
  buttonActionName :string = "Start";
  startName : string = "Start"
  pauzeName: string ="Pauza" 

  text :string[] = []
  typedWords: string[] = []
  constructor(private route: ActivatedRoute,
    private writingService: WritingService,
    public timerService: TimerService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(param=>{
      let idPom = param.get('id')
      let isDrawedPom = param.get('isDrawed')
      if(idPom && isDrawedPom){
        if(isDrawedPom == "true"){
          let levelPom = param.get('level')
          if(levelPom){
            this.writingService.drawTextByLevel(levelPom).subscribe((res:WritingText)=>{
              this.writingText = res
              this.text=this.writingText.text.split(" ")
            })
          }else{
            this.writingService.drawText().subscribe((res:WritingText)=>{
              this.writingText = res
              this.text=this.writingText.text.split(" ")
            })
          }
        }else{
          this.writingService.getTextById(parseInt(idPom)).subscribe((res:WritingText)=>{
            this.writingText = res
            this.text=this.writingText.text.split(" ")
            console.log(res)
          })
        }

      }
    })
  }

  startOrPause(){

  }

  calculateInputWidth(word :string){
    return 9*word.length
  }

  setTextColor(i:number,j:number):string{
    if(this.typedWords.length < i+1){
      return "black"
    }else{
    if(j+1>this.typedWords[i].length){
        return "black"
    }else{
      var char : string = this.typedWords[i].charAt(j)
      if(char == this.text[i].charAt(j)){
        return "green"
      }else{
        return "red"
      }
    }
  }
  }


  moveOn(i: number, word: string){
    if(word.length <= this.typedWords[i-1].length){
      let idStr : string = i.toString()
      document.getElementById(idStr)?.focus()
    }
  }

  getMaxLength(word :string):number{
    console.log(word.length)
    return word.length
  }

  checkIfNewLine(word: string):boolean{
    if(word.charAt(0).toUpperCase() === word.charAt(0)){
      return true
    }else{
      return false
    }
  }

  

}
