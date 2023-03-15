import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemorizngResultComponent } from './memorizng-result.component';

describe('MemorizngResultComponent', () => {
  let component: MemorizngResultComponent;
  let fixture: ComponentFixture<MemorizngResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemorizngResultComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemorizngResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
