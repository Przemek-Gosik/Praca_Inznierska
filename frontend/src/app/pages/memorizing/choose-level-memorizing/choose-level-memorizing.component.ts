import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LevelConsts } from 'src/app/consts/level-consts';
import { TypeMemory } from 'src/app/consts/type-consts';
import { LevelSelect } from 'src/app/models/level-model';

@Component({
  selector: 'app-choose-level-memorizing',
  templateUrl: './choose-level-memorizing.component.html',
  styleUrls: ['./choose-level-memorizing.component.css']
})
export class ChooseLevelMemorizingComponent implements OnInit {
  levels: LevelSelect[] = LevelConsts.LEVEL_SELECT
  chosenLevelName: string = LevelConsts.LEVEL_SELECT[0].value
  type: string = TypeMemory.MEMORY;
  constructor(private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(param=>{
      let typePom = param.get('type')
      if(typePom){
        this.type = typePom
      }
    })
  }

  reset():void{
    this.chosenLevelName = LevelConsts.LEVEL_SELECT[0].value
  }

  startTest(){
    switch(this.type){
      case TypeMemory.MEMORY:
        this.router.navigate(['/courses/memorizing/level/memory',{
          level: this.chosenLevelName
        }])
        break;
      case TypeMemory.MNEMONICS:
        this.router.navigate(['/courses/memorizing/level/mnemonics',{
          level: this.chosenLevelName
        }])
        break;
      default:
        break;
    }
  }

}
