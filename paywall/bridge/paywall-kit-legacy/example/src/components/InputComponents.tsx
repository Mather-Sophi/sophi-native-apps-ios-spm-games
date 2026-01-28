import { View, Text, TextInput, TouchableOpacity } from 'react-native';
import { styles } from '../styles';

// Compact Numeric Input in a single line
export function CompactNumericInput({
  label,
  value,
  onValueChange,
  max = 100,
}: {
  label: string;
  value: number;
  onValueChange: (value: number) => void;
  max?: number;
}) {
  const handleTextChange = (text: string) => {
    const numValue = parseInt(text, 10);
    if (!isNaN(numValue)) {
      const clampedValue = Math.max(0, Math.min(max, numValue));
      onValueChange(clampedValue);
    } else if (text === '') {
      onValueChange(0);
    }
  };

  return (
    <View style={styles.compactInputRow}>
      <Text style={styles.compactLabel}>{label}</Text>
      <TextInput
        style={styles.compactInput}
        value={value.toString()}
        onChangeText={handleTextChange}
        keyboardType="number-pad"
        placeholder="0"
        placeholderTextColor="#999"
      />
    </View>
  );
}

// UI Component for Numeric Input (using TextInput)
export function SliderInput({
  label,
  value,
  onValueChange,
  min = 0,
  max = 100,
}: {
  label: string;
  value: number;
  onValueChange: (value: number) => void;
  min?: number;
  max?: number;
}) {
  const handleTextChange = (text: string) => {
    const numValue = parseInt(text, 10);
    if (!isNaN(numValue)) {
      const clampedValue = Math.max(min, Math.min(max, numValue));
      onValueChange(clampedValue);
    }
  };

  return (
    <View style={styles.inputGroup}>
      <Text style={styles.inputLabel}>{label}</Text>
      <View style={styles.sliderContainer}>
        <TextInput
          style={styles.numericInput}
          value={Math.round(value).toString()}
          onChangeText={handleTextChange}
          keyboardType="number-pad"
          placeholder="0"
          placeholderTextColor="#999"
        />
        <Text style={styles.rangeText}>
          {min} - {max}
        </Text>
      </View>
    </View>
  );
}

// UI Component for Text Input
export function TextInputField({
  label,
  value,
  onChangeText,
  placeholder,
}: {
  label: string;
  value: string | null;
  onChangeText: (text: string) => void;
  placeholder?: string;
}) {
  return (
    <View style={styles.inputGroup}>
      <Text style={styles.inputLabel}>{label}</Text>
      <TextInput
        style={styles.textInput}
        value={value ?? ''}
        onChangeText={onChangeText}
        placeholder={placeholder}
        placeholderTextColor="#999"
      />
    </View>
  );
}

// UI Component for Radio Button Group
export function RadioButtonGroup({
  label,
  options,
  value,
  onSelect,
}: {
  label: string;
  options: string[];
  value: string;
  onSelect: (value: string) => void;
}) {
  return (
    <View style={styles.inputGroup}>
      <Text style={styles.inputLabel}>{label}</Text>
      <View style={styles.radioGroup}>
        {options.map((option) => (
          <TouchableOpacity
            key={option}
            style={styles.radioOption}
            onPress={() => onSelect(option)}
          >
            <View
              style={[
                styles.radioCircle,
                value === option && styles.radioCircleSelected,
              ]}
            >
              {value === option && <View style={styles.radioInner} />}
            </View>
            <Text style={styles.radioLabel}>{option}</Text>
          </TouchableOpacity>
        ))}
      </View>
    </View>
  );
}
