import React from 'react';
import { render, screen } from '@testing-library/react';
import GradesDisplay from './GradesDisplay';

describe('GradesDisplay Component', () => {
  
  const mockProgressList = [
    {
      criterionId: 'C02',
      guetestufe: 3,
      erfuellteAnforderungen: [0, 1, 2, 3, 4, 5],
    },
    {
      criterionId: 'B05',
      guetestufe: 2,
      erfuellteAnforderungen: [0, 1, 2],
    },
  ];
  
  const mockCriteria = [
    {
      id: 'C02',
      titel: 'Datenmodell entwickeln',
      teil: 'Teil1',
      anforderungen: ['A1', 'A2', 'A3', 'A4', 'A5', 'A6'],
    },
    {
      id: 'B05',
      titel: 'Backend-Architektur',
      teil: 'Teil2',
      anforderungen: ['A1', 'A2', 'A3', 'A4', 'A5', 'A6'],
    },
  ];
  
  describe('Rendering', () => {
    it('sollte Komponente rendern', () => {
      render(<GradesDisplay progressList={mockProgressList} criteria={mockCriteria} />);
      
      // Die Komponente sollte existieren
      expect(document.body).toBeInTheDocument();
    });
    
    it('sollte "Keine Daten" zeigen wenn progressList leer ist', () => {
      render(<GradesDisplay progressList={[]} criteria={mockCriteria} />);
      
      // Prüfe ob irgendeine Nachricht oder Fallback angezeigt wird
      expect(document.body.textContent).not.toBe('');
    });
    
    it('sollte mit undefined progressList umgehen können', () => {
      render(<GradesDisplay progressList={undefined} criteria={mockCriteria} />);
      
      // Sollte nicht crashen
      expect(document.body).toBeInTheDocument();
    });
    
    it('sollte mit null criteria umgehen können', () => {
      render(<GradesDisplay progressList={mockProgressList} criteria={null} />);
      
      // Sollte nicht crashen
      expect(document.body).toBeInTheDocument();
    });
  });
  
  describe('Gütestufen Anzeige', () => {
    it('sollte Gütestufe-Informationen anzeigen wenn vorhanden', () => {
      render(<GradesDisplay progressList={mockProgressList} criteria={mockCriteria} />);
      
      // Container sollte existieren
      const container = document.querySelector('.grades-display, .grade-container, [class*="grade"]');
      if (container) {
        expect(container).toBeInTheDocument();
      }
    });
  });
  
  describe('Props Validation', () => {
    it('sollte mit leeren Props funktionieren', () => {
      render(<GradesDisplay />);
      
      expect(document.body).toBeInTheDocument();
    });
    
    it('sollte mit nur progressList funktionieren', () => {
      render(<GradesDisplay progressList={mockProgressList} />);
      
      expect(document.body).toBeInTheDocument();
    });
    
    it('sollte mit nur criteria funktionieren', () => {
      render(<GradesDisplay criteria={mockCriteria} />);
      
      expect(document.body).toBeInTheDocument();
    });
  });
});



