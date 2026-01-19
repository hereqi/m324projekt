import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import CriterionCard from './CriterionCard';

describe('CriterionCard Component', () => {
  
  const mockCriterion = {
    id: 'C02',
    titel: 'Datenmodell entwickeln',
    leitfrage: 'Wurde ein professionelles Datenmodell entwickelt?',
    teil: 'Teil1',
    anforderungen: [
      'Datenmodellierungsmethodik gewählt',
      'Geschäftsanforderungen korrekt abgebildet',
      'Normalisierung angewandt',
      'Flexibel und skalierbar',
      'Ausreichend dokumentiert',
      'Performanceanforderungen erfüllt',
    ],
  };
  
  const mockProgress = {
    id: 1,
    personId: 1,
    criterionId: 'C02',
    erfuellteAnforderungen: [0, 1, 2],
    notizen: 'Meine Notizen',
    guetestufe: 2,
  };
  
  describe('Rendering', () => {
    it('sollte Kriterium-Titel anzeigen', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      expect(screen.getByText(/datenmodell entwickeln/i)).toBeInTheDocument();
    });
    
    it('sollte Kriterium-ID anzeigen', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      expect(screen.getByText(/C02/i)).toBeInTheDocument();
    });
    
    it('sollte alle Anforderungen als Checkboxen anzeigen', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      const checkboxes = screen.getAllByRole('checkbox');
      expect(checkboxes.length).toBe(mockCriterion.anforderungen.length);
    });
    
    it('sollte erfüllte Anforderungen als checked anzeigen', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      const checkboxes = screen.getAllByRole('checkbox');
      
      // Erste 3 sollten checked sein (Index 0, 1, 2)
      expect(checkboxes[0]).toBeChecked();
      expect(checkboxes[1]).toBeChecked();
      expect(checkboxes[2]).toBeChecked();
      
      // Rest sollte nicht checked sein
      expect(checkboxes[3]).not.toBeChecked();
      expect(checkboxes[4]).not.toBeChecked();
      expect(checkboxes[5]).not.toBeChecked();
    });
    
    it('sollte Gütestufe anzeigen', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      // Gütestufe 2 sollte irgendwo angezeigt werden
      expect(screen.getByText(/2|gütestufe/i)).toBeInTheDocument();
    });
    
    it('sollte Notizen anzeigen wenn vorhanden', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      expect(screen.getByDisplayValue(/meine notizen/i)).toBeInTheDocument();
    });
  });
  
  describe('Interactions', () => {
    it('sollte onProgressChange aufrufen wenn Checkbox geklickt wird', () => {
      const onProgressChange = jest.fn();
      
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={onProgressChange}
        />
      );
      
      // 4. Checkbox klicken (Index 3)
      const checkboxes = screen.getAllByRole('checkbox');
      fireEvent.click(checkboxes[3]);
      
      expect(onProgressChange).toHaveBeenCalled();
    });
    
    it('sollte Checkbox unchecken wenn erneut geklickt', () => {
      const onProgressChange = jest.fn();
      
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={onProgressChange}
        />
      );
      
      // Erste Checkbox klicken (war checked, sollte unchecked werden)
      const checkboxes = screen.getAllByRole('checkbox');
      fireEvent.click(checkboxes[0]);
      
      expect(onProgressChange).toHaveBeenCalled();
    });
  });
  
  describe('Edge Cases', () => {
    it('sollte mit leerem Progress funktionieren', () => {
      const emptyProgress = {
        criterionId: 'C02',
        erfuellteAnforderungen: [],
        notizen: '',
        guetestufe: 0,
      };
      
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={emptyProgress}
          onProgressChange={() => {}}
        />
      );
      
      const checkboxes = screen.getAllByRole('checkbox');
      checkboxes.forEach(checkbox => {
        expect(checkbox).not.toBeChecked();
      });
    });
    
    it('sollte mit null Progress funktionieren', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={null}
          onProgressChange={() => {}}
        />
      );
      
      // Sollte nicht crashen
      expect(screen.getByText(/datenmodell entwickeln/i)).toBeInTheDocument();
    });
    
    it('sollte mit undefined Progress funktionieren', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={undefined}
          onProgressChange={() => {}}
        />
      );
      
      expect(screen.getByText(/datenmodell entwickeln/i)).toBeInTheDocument();
    });
  });
});



