import { View, Text } from 'react-native';
import { Platform } from 'react-native';
import { type UserDimensions } from '@mather-sophi/sophi-react-native-paywall-kit';
import { styles } from '../styles';
import { ContextCodes, InputCodes } from '../constants/codes';

type Row = { label: string; value: string | number | null | undefined; code?: string; encoded?: string };

function pad2(n: number | null | undefined): string | undefined {
  if (n === null || n === undefined) return undefined;
  const v = typeof n === 'number' ? n : Number(n);
  if (Number.isNaN(v)) return undefined;
  return v.toString().padStart(2, '0');
}

function createContextRow(label: keyof typeof ContextCodes, value: number | null | undefined): Row {
  const code = ContextCodes[label];
  return {
    label,
    value,
    code,
    encoded: value !== null && value !== undefined ? `${code}${pad2(value)}` : undefined
  };
}

function createInputRow(label: keyof typeof InputCodes, value: string | number | null | undefined, useColon: boolean = true): Row {
  const code = InputCodes[label];
  const formattedValue = typeof value === 'number' ? pad2(value) : value;
  return {
    label,
    value,
    code,
    encoded: value !== null && value !== undefined ? `${code}${useColon ? ':' : ''}${formattedValue}` : undefined
  };
}

function makeRows(
  dimensions: UserDimensions,
  assignedGroup?: string | null,
  userProperties?: Record<string, any> | null,
  contentProperties?: Record<string, any> | null
): { title: string; rows: Row[] }[] {
  const hourOfDay = new Date().getHours();

  const contextRows: Row[] = [
    createContextRow('daysSinceLastVisit', dimensions.daysSinceLastVisit),
    createContextRow('todayPageViews', dimensions.todayPageViews),
    createContextRow('todayPageViewsByArticle', dimensions.todayPageViewsByArticle),
    createContextRow('todayPageViewsByArticleWithPaywall', dimensions.todayPageViewsByArticleWithPaywall),
    createContextRow('todayTopLevelSections', dimensions.todayTopLevelSections),
    createContextRow('todayTopLevelSectionsByArticle', dimensions.todayTopLevelSectionsByArticle),
    createContextRow('todayPageViewsByArticleWithRegwall', dimensions.todayPageViewsByArticleWithRegwall),
    createContextRow('sevenDayPageViews', dimensions.sevenDayPageViews),
    createContextRow('sevenDayPageViewsByArticle', dimensions.sevenDayPageViewsByArticle),
    createContextRow('sevenDayPageViewsByArticleWithPaywall', dimensions.sevenDayPageViewsByArticleWithPaywall),
    createContextRow('sevenDayTopLevelSections', dimensions.sevenDayTopLevelSections),
    createContextRow('sevenDayTopLevelSectionsByArticle', dimensions.sevenDayTopLevelSectionsByArticle),
    createContextRow('sevenDayVisitCount', dimensions.sevenDayVisitCount),
    createContextRow('sevenDayPageViewsByArticleWithRegwall', dimensions.sevenDayPageViewsByArticleWithRegwall),
    createContextRow('twentyEightDayPageViews', dimensions.twentyEightDayPageViews),
    createContextRow('twentyEightDayPageViewsByArticle', dimensions.twentyEightDayPageViewsByArticle),
    createContextRow('twentyEightDayPageViewsByArticleWithPaywall', dimensions.twentyEightDayPageViewsByArticleWithPaywall),
    createContextRow('twentyEightDayTopLevelSections', dimensions.twentyEightDayTopLevelSections),
    createContextRow('twentyEightDayTopLevelSectionsByArticle', dimensions.twentyEightDayTopLevelSectionsByArticle),
    createContextRow('twentyEightDayVisitCount', dimensions.twentyEightDayVisitCount),
    createContextRow('twentyEightDayPageViewsByArticleWithRegwall', dimensions.twentyEightDayPageViewsByArticleWithRegwall),
  ];

  const inputRows: Row[] = [
    createInputRow('visitorType', dimensions.visitorType),
    createInputRow('os', Platform.OS),
    createInputRow('type', undefined),
    createInputRow('viewer', undefined),
    createInputRow('hourOfDay', hourOfDay),
    createInputRow('timeZone', dimensions.timeZone),
    { label: 'referrer', value: dimensions.referrer, code: '—', encoded: undefined },
    createInputRow('referrerMedium', dimensions.referrerData?.medium),
    createInputRow('referrerSource', dimensions.referrerData?.source),
    createInputRow('referrerChannel', dimensions.referrerData?.channel),
    { label: 'assignedGroup', value: assignedGroup, code: '—', encoded: undefined },
    { label: 'userProperties', value: userProperties ? JSON.stringify(userProperties) : null, code: '—', encoded: undefined },
    { label: 'contentProperties', value: contentProperties ? JSON.stringify(contentProperties) : null, code: '—', encoded: undefined },
  ];

  const sessionRows: Row[] = [
    createInputRow('campaignName', dimensions.visitor?.session?.campaignName),
    createInputRow('referrerDomain', dimensions.visitor?.session?.referrerDomain),
    createInputRow('sessionReferrerMedium', dimensions.visitor?.session?.referrerMedium ?? dimensions.referrerData?.medium),
    createInputRow('sessionReferrerSource', dimensions.visitor?.session?.referrerSource ?? dimensions.referrerData?.source),
    createInputRow('sessionReferrerChannel', dimensions.visitor?.session?.referrerChannel ?? dimensions.referrerData?.channel),
  ];

  const experimentRows: Row[] = [];

  return [
    { title: "Context Metrics", rows: contextRows },
    { title: "Inputs", rows: inputRows },
    { title: "Visitor Session", rows: sessionRows },
  ];
}

export function UserDimensionsDisplay({
  dimensions,
  assignedGroup,
  userProperties,
  contentProperties,
}: {
  dimensions: UserDimensions;
  assignedGroup?: string | null;
  userProperties?: Record<string, any> | null;
  contentProperties?: Record<string, any> | null;
}) {
  const sections = makeRows(dimensions, assignedGroup, userProperties, contentProperties);
  return (
    <View style={styles.section}>
      <Text style={styles.sectionTitle}>📊 Current User Dimensions (Tabular with Codes)</Text>
      {sections.map((section) => {
        const showEncoded = section.title !== 'Context Metrics' && section.title !== 'Inputs';
        return (
          <View key={section.title} style={styles.dimensionsBox}>
            <Text style={styles.dimensionLabel}>{section.title}</Text>
            <View style={styles.tableRowHeader}>
              <Text style={[styles.tableCellField, styles.tableHeaderCell]}>Field</Text>
              <Text style={[styles.tableCellShort, styles.tableHeaderCell]}>Value</Text>
              <Text style={[styles.tableCellShort, styles.tableHeaderCell]}>Code</Text>
              {showEncoded && <Text style={[styles.tableCell, styles.tableHeaderCell]}>Encoded</Text>}
            </View>
            {section.rows.map((r) => (
              <View key={`${section.title}-${r.label}`} style={styles.tableRow}>
                <Text style={[styles.tableCellField, styles.tableCellLabel]}>{r.label}</Text>
                <Text style={styles.tableCellShort}>{r.value === undefined || r.value === null ? '—' : String(r.value)}</Text>
                <Text style={styles.tableCellShort}>{r.code ?? '—'}</Text>
                {showEncoded && <Text style={[styles.tableCell, styles.tableCellEncoded]}>{r.encoded ?? '—'}</Text>}
              </View>
            ))}
          </View>
        );
      })}
    </View>
  );
}
