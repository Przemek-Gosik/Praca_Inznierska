import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EyesWarmUpComponent } from './eyes-warm-up.component';

describe('EyesWarmUpComponent', () => {
  let component: EyesWarmUpComponent;
  let fixture: ComponentFixture<EyesWarmUpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EyesWarmUpComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EyesWarmUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
