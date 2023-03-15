import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseLevelMemorizingComponent } from './choose-level-memorizing.component';

describe('ChooseLevelMemorizingComponent', () => {
  let component: ChooseLevelMemorizingComponent;
  let fixture: ComponentFixture<ChooseLevelMemorizingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChooseLevelMemorizingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChooseLevelMemorizingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
