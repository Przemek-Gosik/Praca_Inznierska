import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseLevelReadingComponent } from './choose-level-reading.component';

describe('ChooseLevelReadingComponent', () => {
  let component: ChooseLevelReadingComponent;
  let fixture: ComponentFixture<ChooseLevelReadingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChooseLevelReadingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChooseLevelReadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
