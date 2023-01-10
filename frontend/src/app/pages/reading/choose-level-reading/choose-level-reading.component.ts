import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LevelConsts } from 'src/app/consts/level-consts';
import { TypeMemory, TypeReading } from 'src/app/consts/type-consts';
import { LevelSelect } from 'src/app/models/level-model';

@Component({
  selector: 'app-choose-level-reading',
  templateUrl: './choose-level-reading.component.html',
  styleUrls: ['./choose-level-reading.component.css',
]
})
export class ChooseLevelReadingComponent implements OnInit {

  levels : LevelSelect[] = LevelConsts.LEVEL_SELECT 
  chosenLevelName: string = "EASY"
  type: string = TypeReading.FINDING_NUMBERS
  
  constructor(private router:Router,
    private route:ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(param=>{
      var typePom = param.get('type')
      if(typePom){
        this.type = typePom
      }
    })
  }

  reset():void{
    this.chosenLevelName = "EASY"
  }

  startTest():void{
    switch(this.type){
      case TypeReading.FINDING_NUMBERS:{
        this.router.navigate(["/courses/reading/level/finding_numbers",{
          level: this.chosenLevelName
        }])
        break;
      }
      case TypeReading.SCHULTZ:{
        this.router.navigate(["/courses/reading/level/schultz",{
          level: this.chosenLevelName
        }])
        break;
      }
    }
    
  }

}
