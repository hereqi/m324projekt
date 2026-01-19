import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import CriterionCard from './CriterionCard';

describe('CriterionCard Component', () => {
  
  const mockCriterion = {
    id: 'B05',
    titel: 'Backend-Architektur',
    leitfrage: 'Wurde eine professionelle Architektur implementiert?',
    teil: 'Teil1',
    anforderungen: [
      'Schichtenarchitektur',
      'Dependency Injection',
      'Error Handling',
      'Logging',
      'Tests',
      'Dokumentation',
    ],
  };
  
  const mockProgress = {
    id: 1,
    personId: 1,
    criterionId: 'B05',
    erfuellteAnforderungen: [0, 1, 3],
    notizen: 'Meine Notizen',
    guetestufe: 2,
  };
  
  describe('Rendering', () => {
    test('sollte Kriterium-Titel anzeigen', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      expect(screen.getByText(/Backend-Architektur/i)).toBeInTheDocument();
    });
    
    test('sollte Kriterium-ID anzeigen', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      expect(screen.getByText(/B05/i)).toBeInTheDocument();
    });
    
    test('sollte alle Anforderungen als Checkboxen anzeigen', () => {
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
    
    test('sollte erfüllte Anforderungen als checked anzeigen', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      const checkboxes = screen.getAllByRole('checkbox');
      
      // Index 0, 1, 3 sollten checked sein
      expect(checkboxes[0]).toBeChecked();
      expect(checkboxes[1]).toBeChecked();
      expect(checkboxes[2]).not.toBeChecked();
      expect(checkboxes[3]).toBeChecked();
    });
    
    test('sollte Notizen anzeigen wenn vorhanden', () => {
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
    test('sollte Checkboxen klickbar sein', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={mockProgress}
          onProgressChange={() => {}}
        />
      );
      
      const checkboxes = screen.getAllByRole('checkbox');
      expect(checkboxes[2]).not.toBeChecked();
      
      // Checkbox sollte klickbar sein und sich ändern
      fireEvent.click(checkboxes[2]);
      // State ändert sich durch den Click (wird durch onProgressChange gehandelt)
    });
  });
  
  describe('Edge Cases', () => {
    test('sollte mit leerem Progress funktionieren', () => {
      const emptyProgress = {
        criterionId: 'B05',
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
    
    test('sollte mit null Progress funktionieren', () => {
      render(
        <CriterionCard 
          criterion={mockCriterion} 
          progress={null}
          onProgressChange={() => {}}
        />
      );
      
      expect(screen.getByText(/Backend-Architektur/i)).toBeInTheDocument();
    });
  });
});
