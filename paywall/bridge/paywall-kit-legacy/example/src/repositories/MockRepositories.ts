import { Platform } from 'react-native';
import {
  type DeviceDimensionRepository,
  type DeviceDimensions,
  type UserDimensionRepository,
  type UserDimensions,
} from '@mather-sophi/sophi-react-native-paywall-kit';

export class MockUserDimensionRepository implements UserDimensionRepository {
  constructor(private dimensionsProvider: () => UserDimensions) {}

  getAll(): UserDimensions {
    return this.dimensionsProvider();
  }
}

export class MockDeviceDimensionRepository implements DeviceDimensionRepository {
  getAll(): DeviceDimensions {
    return {
      hourOfDay: new Date().getHours(),
      os: Platform.OS,
      type: 'mobile',
      viewer: 'paywall-kit-example-2.0.0',
    };
  }
}
