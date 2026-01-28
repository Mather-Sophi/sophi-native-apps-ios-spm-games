import { View, Text } from 'react-native';
import { Platform } from 'react-native';
import { type UserDimensions, type WallDecision } from '@mather-sophi/sophi-react-native-paywall-kit';
import { styles } from '../styles';

type Row = { label: string; value: string | number | null | undefined; code?: string; encoded?: string };

function pad2(n: number | null | undefined): string | undefined {
  if (n === null || n === undefined) return undefined;
  const v = typeof n === 'number' ? n : Number(n);
  if (Number.isNaN(v)) return undefined;
  return v.toString().padStart(2, '0');
}

function makeRows(dimensions: UserDimensions, decision?: WallDecision | null): { title: string; rows: Row[] }[] {
  const hourOfDay = new Date().getHours();

  const contextRows: Row[] = [
    { label: 'daysSinceLastVisit', value: dimensions.daysSinceLastVisit, code: 'DA', encoded: `DA${pad2(dimensions.daysSinceLastVisit)}` },
    { label: 'todayPageViews', value: dimensions.todayPageViews, code: 'AA', encoded: `AA${pad2(dimensions.todayPageViews)}` },
    { label: 'todayPageViewsByArticle', value: dimensions.todayPageViewsByArticle, code: 'AB', encoded: `AB${pad2(dimensions.todayPageViewsByArticle)}` },
    { label: 'todayPageViewsByArticleWithPaywall', value: dimensions.todayPageViewsByArticleWithPaywall, code: 'AC', encoded: `AC${pad2(dimensions.todayPageViewsByArticleWithPaywall)}` },
    { label: 'todayTopLevelSections', value: dimensions.todayTopLevelSections, code: 'AD', encoded: `AD${pad2(dimensions.todayTopLevelSections)}` },
    { label: 'todayTopLevelSectionsByArticle', value: dimensions.todayTopLevelSectionsByArticle, code: 'AE', encoded: `AE${pad2(dimensions.todayTopLevelSectionsByArticle)}` },
    { label: 'todayPageViewsByArticleWithRegwall', value: dimensions.todayPageViewsByArticleWithRegwall, code: 'AF', encoded: `AF${pad2(dimensions.todayPageViewsByArticleWithRegwall)}` },
    { label: 'sevenDayPageViews', value: dimensions.sevenDayPageViews, code: 'BA', encoded: `BA${pad2(dimensions.sevenDayPageViews)}` },
    { label: 'sevenDayPageViewsByArticle', value: dimensions.sevenDayPageViewsByArticle, code: 'BB', encoded: `BB${pad2(dimensions.sevenDayPageViewsByArticle)}` },
    { label: 'sevenDayPageViewsByArticleWithPaywall', value: dimensions.sevenDayPageViewsByArticleWithPaywall, code: 'BC', encoded: `BC${pad2(dimensions.sevenDayPageViewsByArticleWithPaywall)}` },
    { label: 'sevenDayTopLevelSections', value: dimensions.sevenDayTopLevelSections, code: 'BD', encoded: `BD${pad2(dimensions.sevenDayTopLevelSections)}` },
    { label: 'sevenDayTopLevelSectionsByArticle', value: dimensions.sevenDayTopLevelSectionsByArticle, code: 'BE', encoded: `BE${pad2(dimensions.sevenDayTopLevelSectionsByArticle)}` },
    { label: 'sevenDayVisitCount', value: dimensions.sevenDayVisitCount, code: 'BF', encoded: `BF${pad2(dimensions.sevenDayVisitCount)}` },
    { label: 'sevenDayPageViewsByArticleWithRegwall', value: dimensions.sevenDayPageViewsByArticleWithRegwall, code: 'BG', encoded: `BG${pad2(dimensions.sevenDayPageViewsByArticleWithRegwall)}` },
    { label: 'twentyEightDayPageViews', value: dimensions.twentyEightDayPageViews, code: 'CA', encoded: `CA${pad2(dimensions.twentyEightDayPageViews)}` },
    { label: 'twentyEightDayPageViewsByArticle', value: dimensions.twentyEightDayPageViewsByArticle, code: 'CB', encoded: `CB${pad2(dimensions.twentyEightDayPageViewsByArticle)}` },
    { label: 'twentyEightDayPageViewsByArticleWithPaywall', value: dimensions.twentyEightDayPageViewsByArticleWithPaywall, code: 'CC', encoded: `CC${pad2(dimensions.twentyEightDayPageViewsByArticleWithPaywall)}` },
    { label: 'twentyEightDayTopLevelSections', value: dimensions.twentyEightDayTopLevelSections, code: 'CD', encoded: `CD${pad2(dimensions.twentyEightDayTopLevelSections)}` },
    { label: 'twentyEightDayTopLevelSectionsByArticle', value: dimensions.twentyEightDayTopLevelSectionsByArticle, code: 'CE', encoded: `CE${pad2(dimensions.twentyEightDayTopLevelSectionsByArticle)}` },
    { label: 'twentyEightDayVisitCount', value: dimensions.twentyEightDayVisitCount, code: 'CF', encoded: `CF${pad2(dimensions.twentyEightDayVisitCount)}` },
    { label: 'twentyEightDayPageViewsByArticleWithRegwall', value: dimensions.twentyEightDayPageViewsByArticleWithRegwall, code: 'CG', encoded: `CG${pad2(dimensions.twentyEightDayPageViewsByArticleWithRegwall)}` },
  ];

  const inputRows: Row[] = [
    { label: 'visitorType', value: dimensions.visitorType, code: 'FA', encoded: `FA:${dimensions.visitorType}` },
    { label: 'os', value: Platform.OS, code: 'EA', encoded: `EA:${Platform.OS}` },
    { label: 'type', value: undefined, code: 'EB', encoded: undefined },
    { label: 'viewer', value: undefined, code: 'EC', encoded: undefined },
    { label: 'hourOfDay', value: hourOfDay, code: 'DD', encoded: `DD:${pad2(hourOfDay)}` },
    { label: 'timeZone', value: dimensions.timeZone, code: 'DB', encoded: `DB:${dimensions.timeZone}` },
    { label: 'referrer', value: dimensions.referrer, code: '—', encoded: undefined },
    { label: 'referrerMedium', value: dimensions.referrerData?.medium, code: 'DE', encoded: `DE:${dimensions.referrerData?.medium}` },
    { label: 'referrerSource', value: dimensions.referrerData?.source, code: 'DF', encoded: `DF:${dimensions.referrerData?.source}` },
    { label: 'referrerChannel', value: dimensions.referrerData?.channel, code: 'DG', encoded: `DG:${dimensions.referrerData?.channel}` },
    { label: 'countryCode', value: dimensions.countryCode ?? null, code: '—', encoded: undefined },
  ];

  const sessionRows: Row[] = [
    { label: 'campaignName', value: dimensions.visitor?.session?.campaignName, code: 'GA', encoded: `GA:${dimensions.visitor?.session?.campaignName ?? ''}` },
    { label: 'referrerDomain', value: dimensions.visitor?.session?.referrerDomain, code: 'GB', encoded: `GB:${dimensions.visitor?.session?.referrerDomain ?? ''}` },
    { label: 'referrerMedium', value: dimensions.visitor?.session?.referrerMedium, code: 'GC', encoded: `GC:${dimensions.visitor?.session?.referrerMedium ?? dimensions.referrerData?.medium ?? ''}` },
    { label: 'referrerSource', value: dimensions.visitor?.session?.referrerSource, code: 'GD', encoded: `GD:${dimensions.visitor?.session?.referrerSource ?? dimensions.referrerData?.source ?? ''}` },
    { label: 'referrerChannel', value: dimensions.visitor?.session?.referrerChannel, code: 'GE', encoded: `GE:${dimensions.visitor?.session?.referrerChannel ?? dimensions.referrerData?.channel ?? ''}` },
  ];

  const experimentRows: Row[] = [];
  if (decision?.experiment) {
    experimentRows.push(
      { label: 'experimentId', value: decision.experiment.experimentId, code: 'HA', encoded: `HA:${decision.experiment.experimentId}` },
      { label: 'assignedGroup', value: decision.experiment.assignedGroup, code: 'HB', encoded: `HB:${decision.experiment.assignedGroup}` },
    );
  }

  return [
    { title: "Context Metrics", rows: contextRows },
    { title: "Inputs", rows: inputRows },
    { title: "Visitor Session", rows: sessionRows },
    { title: "Experiment", rows: experimentRows },
  ];
}

export function UserDimensionsDisplay({ dimensions, decision }: { dimensions: UserDimensions; decision?: WallDecision | null }) {
  const sections = makeRows(dimensions, decision);
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
