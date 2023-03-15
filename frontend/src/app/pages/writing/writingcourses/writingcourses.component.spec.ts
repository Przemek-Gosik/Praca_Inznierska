import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingcoursesComponent } from './writingcourses.component';

describe('WritingcoursesComponent', () => {
  let component: WritingcoursesComponent;
  let fixture: ComponentFixture<WritingcoursesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritingcoursesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritingcoursesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
