import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PickCourseComponent } from './pick-course.component';

describe('PickCourseComponent', () => {
  let component: PickCourseComponent;
  let fixture: ComponentFixture<PickCourseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PickCourseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PickCourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
